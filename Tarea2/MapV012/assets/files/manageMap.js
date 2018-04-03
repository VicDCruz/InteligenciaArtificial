var map; // Mapa de Google Maps
var marcadores=[]; // Pines que estarán en el mapa
var marcadoresSol=[]; // Pines con la solución óptima de la ruta
var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!$%&/()=?¿¡!{}ç'; // Etiquetas para la solución
var labelIndex = 0; // Para obtener el char de labels
var ciudades=document.getElementById('ciudIni'); // Obtiene el DDL del HTML
var distancias=[]; // Matriz de distancias entre cada punto dado un radio de búsqueda
var destinos=[]; // Vector de distancia entre cada punto y el punto FINAL
var lineaPoli=null; // Para trazar la línea poligonal de la solución

// Inicia la matriz de distancias en 0
function initDistancias() {
  distancias=[];
  for (var i = 0; i < ciudades.length; i++) {
    var tmp=[];
    for (var j = 0; j < ciudades.length; j++) {
      tmp[j]=0;
    }
    distancias[i]=tmp;
  }
}

// Inicia el vector de destinos en 0
function initDestinos() {
  destinos=[];
  for (var i = 0; i < ciudades.length; i++) {
    destinos[i]=0;
  }
}

// Pone todos los marcadores en el MAP
// Si MAP=null => Oculta todos los pines
function setMapOnAll(map) {
  for (var i = 0; i < marcadores.length; i++) {
    marcadores[i].setMap(map);
  }
}

// Ponemos la localización de cada ciudad que es solución de la ruta en marcadoresSol
function addSolve(location, i) {
  var pinImage='https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
  var marker = new google.maps.Marker({
    position: location,
    label: labels[labelIndex++ % labels.length],
    icon: pinImage,
    map: map
  });
  marcadoresSol.push(marker);
}

// Añadimos una localización y ponemos el nombre de ese lugar en la descripción
function addMarker(location, lugar) {
  var infowindow = new google.maps.InfoWindow({
    content: '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<div id="bodyContent">'+
      '<p><b>'+lugar+'</b></p>'+
      '</div>'+
      '</div>'
  });

  var marker = new google.maps.Marker({
    position: location,
    title: lugar,
    map: map
  });
  // Despliega el nombre del lugar al hacer click
  marker.addListener('click', function() {
    infowindow.open(map, marker);
  });
  // labels[labelIndex++ % labels.length]
  marcadores.push(marker);
}

// Ocultamos todoso los marcadores
function clearMarkers() {
  setMapOnAll(null);
}

// Desligamos los marcadores del mapa y después los quitamos
function deleteMarkers() {
  clearMarkers();
  marcadores = [];
}

// Obtenemos las coordenadas de un lugar dado su nombre
// Nota: Este servicio no puede ser continuo, así que ponemos un timeout
// de 1seg para no saturar la respuesta
function getCoord(lugar) {
  var geocoder = new google.maps.Geocoder();

  return new Promise(function (resolve, reject) {
    setTimeout(function () {
      geocoder.geocode({'address':lugar}, function(res, status){
        if (status === 'OK') {
          addMarker(res[0].geometry.location, lugar);
          resolve(res[0].geometry.location);
        } else {
          console.log("Error en getCoord, "+lugar);
          reject( Error('Geocode no pudo encontrar el lugar porque: ' + status));
        }
      });
    }
    ,1000);
  });
}

// Obtenemos en forma de vector el Lat y Lng de un marcador
function getLatLng(index) {
  var res=[];
  res[0]=marcadores[index].getPosition().lat();
  res[1]=marcadores[index].getPosition().lng();

  return res;
}

// Inicializamos el mapa con los marcadores y las distancias y los destinos
async function initMap() {
  var center = {lat: 19.432618, lng: -99.133210}; // Pin a CDMX (Centro)
  map = new google.maps.Map(document.getElementById('map'), {
    zoom:4,
    center: center
  });
  await placeDLLMarkers();
  initDistancias();
  initDestinos();
  // var eg = await dest2Str("Toluca");
  console.log(distancias);
  document.getElementById('btnRuta').disabled=false;
  document.getElementById('progress').innerHTML="";
  // alert("¡LISTO para usarse!");
}

async function placeDLLMarkers(){
  var progBar=document.getElementById('progressBarFront');
  var tmpC=[];
  var uptC=[];
  for (var i = 0; i < ciudades.length; i++) {
    tmpC[i]='"'+ciudades.options[i].text+'"';
  }
  return new Promise(async function(resolve, reject) {
    // for (var i = 0; i < ciudades.length; i++) {
    //   var width="width:"+(i+1)*100/ciudades.length+"%";
    //   console.log(ciudades.options[i].text);
    //
    //   progBar.style=width;
    //   var res=await getCoord(ciudades.options[i].text);
    // }
    var proc=0;

    var data=await new Promise(function(resolve, reject) {
      httpPost('getCiudades','ciudades=['+tmpC+']', function(datos){
        resolve(datos);
      });
    });

    var res=JSON.parse(data);
    for (var i = 0; i < res.completados.length; i++) {
      var point = new google.maps.LatLng(
        parseFloat(res.completados[i].lat),
        parseFloat(res.completados[i].lng));
      addMarker(point, res.completados[i].nom);
      var width="width:"+proc*100/ciudades.length+"%";
      proc++;
      progBar.style=width;
    };
    for (var i = 0; i < res.faltantes.length; i++) {
      var resCoord=await getCoord(res.faltantes[i]);
      uptC[i]={nom: res.faltantes[i],lat: resCoord.lat(),lng: resCoord.lng()};
      var width="width:"+proc*100/ciudades.length+"%";
      proc++;
      progBar.style=width;
    };

    if (uptC.length!=0) {
      httpPost('updateCiudades','ciudades='+JSON.stringify(uptC),function(data){ });
    }

    setMapOnAll(map);
    resolve("Listo en placeDLLMarkers");
  });
}

function getRuta() {
  // deleteMarkers();

  var distLow=document.getElementById('distLow').value;
  var distHigh=document.getElementById('distHigh').value;
  distXCiudad(Number(distLow),Number(distHigh));
  
  var ciudIni=document.getElementById('ciudIni');
  var ciudFin=document.getElementById('ciudFin');

  var inicio=ciudIni.options[ciudIni.selectedIndex].text.replace(/ /g,"");
  var fin=ciudFin.options[ciudFin.selectedIndex].text
  console.log("Inicio: "+inicio+", Fin: "+fin);
  var ciudadA=ciudad2Str();
  var distanciasA=dist2Str();
  destXCiudad(fin);
  fin=fin.replace(/ /g,"");
  var destinosA=dest2Str();

  document.getElementById('progress').innerHTML='<img src="/images/loading.gif" alt="Cargando…" height="42" width="42">';

  httpPost('result','ciudades='+ciudadA+'&distancias='+
    distanciasA+'&destinos='+destinosA+'&inicio='+inicio+'&fin='+fin,function(data){
      document.getElementById('progress').innerHTML="";

      var dataTrans=JSON.parse(data);
      var km=dataTrans.km;
      var result=dataTrans.ruta;
      buscaMarcSol(result);
      var front=document.getElementById("resultadosFront");
      front.innerHTML="";
      var str="";
      str=str+"<h2 class='display-4'>Resumen</h2>";
      str=str+'<ol type="A">';
      for (ciudad of result) {
        str=str+"<li>"+ciudad+"</li>";
      }
      str=str+"</ol>";
      str=str+"Distancia: "+km+" km";
      str=str+"<br>Tiempo: "+(km/100).toFixed(5)+" hrs.";
      front.innerHTML=str;
  });
}

function httpPost(url, arg, callback){
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      callback(this.responseText);
    }
  };
  xhttp.open("POST", url, true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  // Se estructura como un GET
  xhttp.send(arg);
}

async function buscaMarcSol(arrSol) {
  labelIndex=0;
  for (var i = 0; i < marcadoresSol.length; i++) {
    marcadoresSol[i].setMap(null);
  }
  marcadoresSol=[];

  var lineaRecorrido=[];

  for (var i = 0; i < arrSol.length; i++) {
    var ciudad=arrSol[i];
    var j=0;
    var status=false;
    while (j<ciudades.length&&!status) {
      var tmp=ciudades.options[j].text.replace(/ /g,"").toUpperCase();
      status=(ciudad===tmp);
      if(!status)
        j++;
    }
    if (j<ciudades.length) {
      var lugar=ciudades.options[j].text;
      var geocoder = new google.maps.Geocoder();

      await new Promise(function (resolve, reject) {
        setTimeout(function () {
          geocoder.geocode({'address':lugar}, function(res, status){
            if (status === 'OK') {
              var location=res[0].geometry.location;
              addSolve(location,j);
              lineaRecorrido.push({"lat":marcadores[j].getPosition().lat(),"lng":marcadores[j].getPosition().lng()});
              resolve("OK");
            } else {
              console.log("Error en getCoord");
              reject( Error('Geocode no pudo encontrar el lugar porque: ' + status));
            }
          });
        }
        ,1000);
      });
    }
  }
  if (lineaPoli!=null) {
    lineaPoli.setMap(null);
  }
  lineaPoli = new google.maps.Polyline({
    path: lineaRecorrido,
    geodesic: true,
    strokeColor: '#0000FF',
    strokeOpacity: 1.0,
    strokeWeight: 3
  });

  setMapOnAll(null);
  lineaPoli.setMap(map);

}

function ciudad2Str(){
  var res="(";

  for (var i = 0; i < ciudades.length; i++) {
    res+=ciudades.options[i].text.replace(/ /g,"")+' ';
  }

  res=res.substr(0, res.length-1)+')';

  return res;
}

function getDistRect(inicio, fin){
  var coord1;
  var coord2;

  coord1=getLatLng(inicio);
  coord2=getLatLng(fin);

  return Math.sqrt(Math.pow(coord1[0]-coord2[0],2)+Math.pow(coord1[1]-coord2[1],2))*100;

  // getLatLng(fin).then(function(res){
  //   coord2=res.replace("(","").replace(")","").split(",");
  // }, function(error){
  //   console.log(error);
  // });

}

function distXCiudad(distLow, distHigh) {
  var i=0;
  var j=0;

  while (i<ciudades.length) {
    while (j<ciudades.length && i!=j) {
      var res=getDistRect(ciudades.options[i].value, ciudades.options[j].value);
      if (distLow<=res&&res<=distHigh) {
        distancias[i][j]=res;
        distancias[j][i]=res;
      }
      j++;
    }
    j=0;
    i++;
  }

}

function dist2Str(){
  var res="(";

  for (distancia of distancias) {
    res+="(";
    for (elem of distancia) {
      res+=elem+" ";
    }
    res=res.substr(0, res.length-1)+") ";
  }

  res=res.substr(0, res.length-1)+")";

  return res;
}

function destXCiudad(fin) {
  var indice=0;
  var status=false;
  var i=0;
  while(i<ciudades.length&&!status){
    if(ciudades.options[i].text==fin){
      status=true;
      indice=i;
    }
    i++
  }
  for (var i = 0; i < ciudades.length; i++) {
    var res= getDistRect(ciudades.options[i].value,indice);
    destinos[i]=res;
  }

  return "OK";
}

function dest2Str() {
  var res="(";

  for (elem of destinos) {
    res+=elem+" ";
  }

  res=res.substr(0, res.length-1)+")";

  return res;
}

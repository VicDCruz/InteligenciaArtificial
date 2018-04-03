/**
 * MapaController
 *
 * @description :: Server-side actions for handling incoming requests.
 * @help        :: See https://sailsjs.com/docs/concepts/actions
 */

 module.exports = {
 	'fillData':function (req, res){
 		const fs = require('fs');

 		// var ciudades=["CDMX", "Toluca", "Querétaro", "Veracruz"];

 		// var ciudades=["Acapulco de Juárez", "Aguascalientes", "Apodaca", "Buenavista Estado de Mexico",
 		// "Cancún", "Celaya", "Chalco de Díaz Covarrubias", "Chetumal", "Chicoloapan",
 		// "Chihuahua", "Chilpancingo de los Bravo", "Chimalhuacán", "Ciudad Acuña",
 		// "Ciudad Cuauhtémoc", "Ciudad de México", "Ciudad del Carmen", "Ciudad Juárez",
 		// "Ciudad López Mateos", "Ciudad Madero", "Ciudad Obregón", "Ciudad Valles",
 		// "Ciudad Victoria", "Coatzacoalcos", "Colima", "Córdoba Veracruz", "Cuautitlán",
 		// "Cuautitlán Izcalli", "Cuautla de Morelos", "Cuernavaca", "Culiacán Rosales",
 		// "Delicias", "Ecatepec", "Ensenada", "Fresnillo", "General Escobedo", "Gómez Palacio",
 		// "Guadalajara", "Guadalupe Baja California", "Guadalupe Nuevo León", "Guadalupe Zacatecas", "Guaymas", "Hermosillo", "Heroica Matamoros",
 		// "Heroica Nogales", "Hidalgo del Parral", "Iguala", "Irapuato", "Ixtapaluca", "Jiutepec",
 		// "Juárez", "La Paz Baja California Sur", "León Guanajuato", "Los Mochis", "Manzanillo", "Mazatlán", "Mérida", "Mexicali",
 		// "Minatitlán", "Miramar Tamaulipas", "Monclova", "Monterrey", "Morelia", "Naucalpan de Juárez",
 		// "Naucalpan de Juárez", "Navojoa", "Nezahualcóyotl", "Nuevo Laredo", "Oaxaca de Juárez",
 		// "Ojo de Agua", "Orizaba", "Pachuca de Soto", "Piedras Negras", "Playa del Carmen",
 		// "Poza Rica de Hidalgo", "Puebla de Zaragoza", "Puerto Vallarta", "Reynosa", "Salamanca Guanajuato",
 		// "Saltillo", "San Cristóbal de las Casas", "San Francisco Coacalco", "San Francisco de Campeche",
 		// "San Juan Bautista Tuxtepec", "San Juan del Río", "San Luis Potosí", "San Luis Río Colorado",
 		// "San Nicolás de los Garza", "San Pablo de las Salinas", "San Pedro Garza García",
 		// "Santa Catarina", "Santiago de Querétaro", "Soledad de Graciano Sánchez", "Tampico",
 		// "Tapachula", "Tehuacán", "Tepexpan", "Tepic", "Texcoco de Mora", "Tijuana",
 		// "Tlalnepantla de Baz", "Tlaquepaque", "Toluca de Lerdo", "Tonalá", "Torreón",
 		// "Tulancingo de Bravo", "Tuxtla Gutiérrez", "Uruapan", "Veracruz", "Victoria de Durango",
 		// "Villa de Álvarez", "Villa Nicolás Romero", "Villahermosa", "Xalapa", "Xico", "Zacatecas",
 		// "Zamora Michoacán", "Zapopan", "La Misión Baja California", "Puerto Nuevo Baja California",
 		// "Valle de Palmas Baja California", "Ejido Ojo de Agua Baja California", "La Joya Baja California",
 		// "Tecate Baja California", "El Rancho Baja California", "La Rumorosa Baja California", "Laguna Salada",
 		// "Ejido el Choropo Baja California", "Ejido de Chihuahua Baja California", "Ejido de Jalapa Baja California",
 		// "Ejido de Tlaxcala Baja California", "Batáquez Baja California", "El doctor Sonora",
 		// "Golfo de Santa Clara Sonora", "El tornillal Sonora", "Reserva de la Biosfera El Pinacate y Gran Desierto de Altar",
 		// "Los Vidrios Sonora", "Sonoyta Sonora", "Quitovac Sonora", "Cozón Sonora", "Puerto Peñasco Sonora",
 		// "Playa Encanto Sonora", "Rancho El Santuario", "Último Esfuerzo Sonora"];

 		// var ciudades=[
 			// "Acacoyagua Chiapas","Acala Chiapas","Acapetahua Chiapas",
 			//     "Aldama Chiapas","Altamirano Chiapas","Amatán Chiapas","Amatenango de la Frontera Chiapas",
 			//     "Amatenango del Valle Chiapas","Angel Albino Corzo Chiapas","Arriaga Chiapas",
 			//     "Bejucal de Ocampo Chiapas","Bella Vista Chiapas","Benemérito de las Américas Chiapas",
 			//     "Berriozábal Chiapas","Bochil Chiapas","Cacahoatán Chiapas","Catazajá Chiapas",
 			//     "Chalchihuitán Chiapas","Chamula Chiapas","Chanal Chiapas","Chapultenango Chiapas",
 			//     "Chenalhó Chiapas","Chiapa de Corzo Chiapas","Chiapilla Chiapas","Chicoasén Chiapas",
 			//     "Chicomuselo Chiapas","Chilón Chiapas","Cintalapa Chiapas","Coapilla Chiapas",
 			//     "Comitán de Domínguez Chiapas","Copainalá Chiapas","El Bosque Chiapas","El Porvenir Chiapas",
 			//     "Escuintla Chiapas","Francisco León Chiapas","Frontera Comalapa Chiapas",
 			//     "Frontera Hidalgo Chiapas","Huehuetán Chiapas","Huitiupán Chiapas","Huixtán Chiapas",
 			//     "Huixtla Chiapas","Ixhuatán Chiapas","Ixtacomitán Chiapas","Ixtapa Chiapas",
 			//     "Ixtapangajoya Chiapas","Jiquipilas Chiapas","Jitotol Chiapas","Juárez Chiapas",
 			//     "La Concordia Chiapas","La Grandeza Chiapas","La Independencia Chiapas","La Libertad Chiapas","La Trinitaria Chiapas","Larráinzar Chiapas","Las Margaritas Chiapas","Las Rosas Chiapas","Mapastepec Chiapas","Maravilla Tenejapa Chiapas","Marqués de Comillas Chiapas","Mazapa de Madero Chiapas","Mazatán Chiapas","Metapa Chiapas","Mitontic Chiapas","Montecristo de Guerrero Chiapas","Motozintla Chiapas","Nicolás Ruíz Chiapas","Ocosingo Chiapas","Ocotepec Chiapas","Ocozocoautla de Espinosa Chiapas","Ostuacán Chiapas","Osumacinta Chiapas","Oxchuc Chiapas","Palenque Chiapas",
 			//     "Pantelhó Chiapas","Pantepec Chiapas","Pichucalco Chiapas","Pijijiapan Chiapas",
 			//     "Pueblo Nuevo Solistahuacán Chiapas","Rayón Chiapas","Reforma Chiapas","Sabanilla Chiapas",
 			//     "Salto de Agua Chiapas","San Andrés Duraznal Chiapas","San Cristóbal de las Casas Chiapas",
 			//     "San Fernando Chiapas","San Juan Cancuc Chiapas","San Lucas Chiapas","Santiago el Pinar Chiapas"
 		// ];

 		var ciudades=[
 			"Álvaro Obregón Distrito Federal",
 			"Azcapotzalco Distrito Federal",
 			"Benito Juárez Distrito Federal",
 			"Coyoacán Distrito Federal",
 			"Cuajimalpa de Morelos Distrito Federal",
 			"Cuauhtémoc Distrito Federal",
 			"Gustavo Madero Distrito Federal",
 			"Iztacalco Distrito Federal",
 			"Iztapalapa Distrito Federal",
 			"La Magdalena Contreras Distrito Federal",
 			"Miguel Hidalgo Distrito Federal",
 			"Milpa Alta Distrito Federal",
 			"Tláhuac Distrito Federal",
 			"Tlalpan Distrito Federal",
 			"Venustiano Carranza Distrito Federal",
 			"Xochimilco Distrito Federal",
 			"Amacuzac Morelos",
 			"Atlatlahucan Morelos",
 			"Axochiapan Morelos",
 			"Ayala Morelos",
 			"Coatlán del Río Morelos",
 			"Cuautla Morelos",
 			"Cuernavaca Morelos",
 			"Emiliano Zapata Morelos",
 			"Huitzilac Morelos",
 			"Jantetelco Morelos",
 			"Jiutepec Morelos",
 			"Jojutla Morelos",
 			"Jonacatepec Morelos",
 			"Mazatepec Morelos",
 			"Miacatlán Morelos",
 			"Ocuituco Morelos",
 			"Puente de Ixtla Morelos",
 			"Temixco Morelos",
 			"Temoac Morelos",
 			"Tepalcingo Morelos",
 			"Tepoztlán Morelos",
 			"Tetecala Morelos",
 			"Tetela del Volcán Morelos",
 			"Tlalnepantla Morelos",
 			"Tlaltizapán de Zapata Morelos",
 			"Tlaquiltenango Morelos",
 			"Tlayacapan Morelos",
 			"Totolapan Morelos",
 			"Xochitepec Morelos",
 			"Yautepec Morelos",
 			"Yecapixtla Morelos",
 			"Zacatepec Morelos",
 			"Zacualpan Morelos",
 			"Acambay de Ruíz Castañeda Estado De México",
 			"Acolman Estado De México",
 			"Aculco Estado De México",
 			"Almoloya de Alquisiras Estado De México",
 			"Almoloya de Juárez Estado De México",
 			"Almoloya del Río Estado De México",
 			"Amanalco Estado De México",
 			"Amatepec Estado De México",
 			"Amecameca Estado De México",
 			"Apaxco Estado De México",
 			"Atenco Estado De México",
 			"Atizapán Estado De México",
 			"Atizapán de Zaragoza Estado De México",
 			"Atlacomulco Estado De México",
 			"Atlautla Estado De México",
 			"Axapusco Estado De México",
 			"Ayapango Estado De México",
 			"Calimaya Estado De México",
 			"Capulhuac Estado De México",
 			"Chalco Estado De México",
 			"Chapa de Mota Estado De México",
 			"Chapultepec Estado De México",
 			"Chiautla Estado De México",
 			"Chicoloapan Estado De México",
 			"Chiconcuac Estado De México",
 			"Chimalhuacán Estado De México",
 			"Coacalco de Berriozábal Estado De México",
 			"Coatepec Harinas Estado De México",
 			"Cocotitlán Estado De México",
 			"Coyotepec Estado De México",
 			"Cuautitlán Estado De México",
 			"Cuautitlán Izcalli Estado De México",
 			"Donato Guerra Estado De México",
 			"Ecatepec de Morelos Estado De México",
 			"Ecatzingo Estado De México",
 			"El Oro Estado De México",
 			"Huehuetoca Estado De México",
 			"Hueypoxtla Estado De México",
 			"Huixquilucan Estado De México",
 			"Isidro Fabela Estado De México",
 			"Ixtapaluca Estado De México",
 			"Ixtapan de la Sal Estado De México",
 			"Ixtapan del Oro Estado De México",
 			"Ixtlahuaca Estado De México",
 			"Jalatlaco Estado De México",
 			"Jaltenco Estado De México",
 			"Jilotepec Estado De México",
 			"Jilotzingo Estado De México",
 			"Jiquipilco Estado De México",
 			"Jocotitlán Estado De México",
 			"Joquicingo Estado De México",
 			"Juchitepec Estado De México",
 			"La Paz Estado De México",
 			"Lerma Estado De México",
 			"Luvianos Estado De México",
 			"Malinalco Estado De México",
 			"Melchor Ocampo Estado De México",
 			"Metepec Estado De México",
 			"Mexicaltzingo Estado De México",
 			"Naucalpan de Juárez Estado De México",
 			"Nextlalpan Estado De México",
 			"Nezahualcóyotl Estado De México",
 			"Nicolás Romero Estado De México",
 			"Nopaltepec Estado De México",
 			"Ocoyoacac Estado De México",
 			"Ocuilan Estado De México",
 			"Otumba Estado De México",
 			"Otzoloapan Estado De México",
 			"Otzolotepec Estado De México",
 			"Ozumba Estado De México",
 			"Papalotla Estado De México",
 			"Polotitlán Estado De México",
 			"Rayón Estado De México",
 			"San Antonio la Isla Estado De México",
 			"San Felipe del Progreso Estado De México",
 			"San José del Rincón Estado De México",
 			"San Martín de las Pirámides Estado De México",
 			"San Mateo Atenco Estado De México",
 			"San Simón de Guerrero Estado De México",
 			"Santo Tomás Estado De México",
 			"Soyaniquilpan de Juárez Estado De México",
 			"Sultepec Estado De México",
 			"Tecámac Estado De México",
 			"Tejupilco Estado De México",
 			"Temamatla Estado De México",
 			"Temascalapa Estado De México",
 			"Temascalcingo Estado De México",
 			"Temascaltepec Estado De México",
 			"Temoaya Estado De México",
 			"Tenancingo Estado De México",
 			"Tenango del Aire Estado De México",
 			"Tenango del Valle Estado De México",
 			"Teoloyucan Estado De México",
 			"Teotihuacán Estado De México",
 			"Tepetlaoxtoc Estado De México",
 			"Tepetlixpa Estado De México",
 			"Tepotzotlán Estado De México",
 			"Tequixquiac Estado De México",
 			"Texcaltitlán Estado De México",
 			"Texcalyacac Estado De México",
 			"Texcoco Estado De México",
 			"Tezoyuca Estado De México",
 			"Tianguistenco Estado De México",
 			"Timilpan Estado De México",
 			"Tlalmanalco Estado De México",
 			"Tlalnepantla de Baz Estado De México",
 			"Tlatlaya Estado De México",
 			"Toluca Estado De México",
 			"Tonanitla Estado De México",
 			"Tonatico Estado De México",
 			"Tultepec Estado De México",
 			"Tultitlán Estado De México",
 			"Valle de Bravo Estado De México",
 			"Valle de Chalco Solidaridad Estado De México",
 			"Villa de Allende Estado De México",
 			"Villa del Carbón Estado De México",
 			"Villa Victoria Estado De México",
 			"Xonacatlán Estado De México",
 			"Zacazonapan Estado De México",
 			"Zacualpan Estado De México",
 			"Zinacantepec Estado De México",
 			"Zumpahuacán Estado De México",
 			"Zumpango Estado De México"
 		];

 		console.log(ciudades.length);

 		var completados=[];
 		var faltantes=[];

 		return res.view('mapa',{
 			'ciudades':ciudades,
 			'completados':completados,
 			'faltantes':faltantes
 		});
 	},

 	'result':function(req, res){
 		const fs = require('fs');
 		const exec = require('child_process').exec;

 		var ciudades=req.param("ciudades");
 		var distancias=req.param("distancias");
 		var destinos=req.param("destinos");
 		var inicio=req.param("inicio");
 		var fin=req.param("fin");

 		var txt=ciudades+"\n"+distancias+"\n"+destinos+"\n"+inicio+"\n"+fin;

 		fs.writeFile('../infoLisp.txt',txt,function(err){
 			if (err) throw err;
 			console.log("infoLisp.txt, GUARDADO!");
 			console.log("Ejecutando CLISP");
 			exec("clisp ../Lisp/aStarSearch.lisp", function(error, stdout, stderr){
 				console.log("Terminó CLISP");
 				fs.readFile("../solLisp.txt",'utf8',function(err, data){
 					var result=data.replace(/\n/g,"");
 					var km=result.substring(result.indexOf(")")+1,result.length);
 					km=Number(km.replace(/ /g,""));
 					result=result.substring(0, result.indexOf(")")+1);
 					result=result.replace("(","").replace(")","").split(" ");
 					var json={"ruta":result, "km":km};
 					return res.send(JSON.stringify(json));
 				});
 			});
 		});

 		// return res.send(req.allParams());
 	},

 	'getCiudades':function(req,res){
 		const fs = require('fs');

 		if (typeof(req.param("ciudades"))=="string")
 			var ciudades=JSON.parse(req.param("ciudades"));
 		else
 			var ciudades=req.param("ciudades");
 		var completados=[];
 		var faltantes=[];
 		var cont=0;

 		fs.readFile("./assets/files/ciudades.json", function(err, data){
 			var content=JSON.parse(data);
 			// Formato de un elemento del JSON en el arreglo content
 			// {
 			// 	nom:Tlapan,
 			// 	lat:329,
 			// 	lng:382
 			// }

 			for (var i = 0; i < ciudades.length; i++){
 				var status=false;
 				var j=0;
 				while (j<content.length && !status) {
 					if(content[j].nom==ciudades[i]){
 						status=true;
 						completados[i]=content[j];
 					}
 					j++;
 				}
 				if (!status) {
 					faltantes[cont]=ciudades[i];
 					cont++
 				}
 			}

 			var json={"completados":completados, "faltantes":faltantes};
 			return res.send(JSON.stringify(json))
 		});
 	},

 	'updateCiudades':function(req,res){
 		const fs = require('fs');

 		var ciudades=req.param("ciudades").toString();
 		ciudades=ciudades.replace("[","").replace("\n","");
 		if (ciudades.charAt(ciudades.length-1)!=']') {
 			ciudades+=']';
 		}

 		fs.readFile("./assets/files/ciudades.json",function(err, data){
 			var str=data.toString().replace("]","")+","+ciudades;
 			fs.writeFile("./assets/files/ciudades.json", str, function(err){
 				if(err) throw err;
 				return res.send("OK");
 			});
 		})
 	},
 };

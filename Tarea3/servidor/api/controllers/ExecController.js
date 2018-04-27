/**
 * ExecController
 *
 * @description :: Server-side actions for handling incoming requests.
 * @help        :: See https://sailsjs.com/docs/concepts/actions
 */

module.exports = {
  'useLisp':function(req, res){
    const exec = require('child_process').exec;

    console.log("infoLisp.txt, GUARDADO!");
    console.log("Ejecutando CLISP");
    exec("clisp /Users/daniel/Documents/InteligenciaArtificial/Tarea3/Lisp/alfaBetaSearch.lisp", function(error, stdout, stderr){
      console.log("Terminó CLISP");
      return res.send("OK");
    });
  }

};

var http = require('./cgi');
var url = require('url');
var app = require('express');
var mysql = require('mysql');
var qs = require('querystring');
var config = require('./config');
const PORT = 3030;

var pool = mysql.createPool({
    connectionLimit: 100,
    host: config.host,
    user: config.user,
    password: config.password,
    database: config.database,
    debug: 'false',
    charset: "utf8_general_ci"
});

function handle_database(req,res,user,pass,query) {
    
    pool.getConnection(function(err,connection){
        if (err) {
          res.write({"code" : 100, "state" : "Error in connection database"});
          return;
        }   

        console.log('connected as id ' + connection.threadId);
        var correct=false;
        connection.query("SELECT haslo FROM pracownicy WHERE mail='"+user+"'",function(err,rows){
            if(!err) {
                if(rows[0] && rows[0].haslo)
                    if(rows[0].haslo===pass) correct=true;
                         
                if(correct){
                    connection.query(query,function(err,resRows){
                        connection.release();
                        if(!err) {
                            console.log(JSON.stringify(resRows));
                            res.writeHead(200, {"Content-Type": "application/json; charset=UTF-8"});
                            res.write(JSON.stringify(resRows).toString("UTF-8"), "UTF-8");
                            res.end();
                        }           
                    });
                } else res.write({"code" : 100, "state" : "Error with logging in"}.toString(), function(err) { res.end(); });
            }           
            
        });
        

        connection.on('error', function(err) {      
              res.write({"code" : 100, "state" : "Error in connection database"});
              return;     
        });
  });
}


function handleRequest(request, response){
    var orderDate = '';
    if(request.method == "POST"){
        request.setEncoding("UTF-8");
        request.on('orderDate', function (_data) {
            orderDate += _data;
        });
        request.on('end', function(){
            orderDate = qs.parse(orderDate);
            handle_database(request, response, orderDate.user, orderDate.pass, orderDate.query);
            
        });
    }
}

/*
function getRoute(request){
    var pathname = url.parse(request.url).pathname;
    var paths = pathname.split("/");
    paths.reverse().pop();
    return paths.pop();
}
*/
var server = http.createServer(handleRequest);

server.listen(PORT, function(){
	console.log("Serwer aktywny. Port: %s",PORT);
});

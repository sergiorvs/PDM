// Your web app's Firebase configuration
var firebaseConfig = {
    apiKey: "AIzaSyCLmQO4WKfjXEzje5ak-EiurICu7xrqFNw",
    authDomain: "justdance-90af3.firebaseapp.com",
    databaseURL: "https://justdance-90af3.firebaseio.com",
    projectId: "justdance-90af3",
    storageBucket: "justdance-90af3.appspot.com",
    messagingSenderId: "1043099350729",
    appId: "1:1043099350729:web:69aa3145cee4af5ea67f18",
    measurementId: "G-EB70VH7P61"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);
firebase.analytics();

database = firebase.database();
var ref = database.ref('servidores/8080');//.orderByKey(); 

function createRoom(){
    var refT = database.ref('servidores');
    refT = refT.child('8080');
    refT.update({
        Started:0,
        nPlayers:0,
        players:{}
    });
}

function getUserNamebyKey(keyUser){
    
}


/* Function for testing */
function setData(pointNEW, userNumb){
    var ref2 = database.ref('game-point/user'+userNumb).set({
        actualPoint: pointNEW,
        numUser: userNumb,
        scoreTotal: 200
    });
}
/* Function for testing End */


ref.on('value', gotData, error);

function gotData(data){
   var scoreeee = data.val()
   var keys = Object.keys(scoreeee);
   console.log({Object});
   keys.forEach(function(userKey,i){
    showNameUsers(i+1, userKey);
   });

   data.forEach(function(d){
       var actPoint = d.val().actualPoint;
       var numPlayer = d.val().numUser;
       var keyUser =  d.key;
       keyUser = keyUser.slice(keyUser.length - 1, keyUser.length);
       console.log( actPoint+" - "+ keyUser);
       showImageActualPoint(actPoint, keyUser);   
   });
}  

function error(err){
    console.log("error");
}

//console.log(ref);

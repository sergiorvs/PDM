//$(document).ready(function () {
    var TotalPlayers = 4;
    
    function showImageActualPoint(typePoint, numPlayer ){
        console.log(numPlayer);
        if (typePoint == 1){
            $('#imgP'+numPlayer).attr('src','TypePoints/perfect1.png');
        }else if (typePoint == 2){
            $('#imgP'+numPlayer).attr('src','TypePoints/good2.png');   
        }else{
            $('#imgP'+numPlayer).attr('src','TypePoints/bad3.png');
        }
    }

    function showNameUsers(numUser, nameUser){
        $("#nameUser"+numUser).text(nameUser)
    }
    
    /*$('div.col.imageContainerP1').click(function () {            
        showImageActualPoint(1,'1');
    });   
    $('div.col.imageContainerP2').click(function () {            
        showImageActualPoint(3,'2');
    });   
    $('div.col.imageContainerP3').click(function () {            
        showImageActualPoint(2,'3');
    });   
    $('div.col.imageContainerP4').click(function () {            
        showImageActualPoint(1,'4');
    });                

});*/
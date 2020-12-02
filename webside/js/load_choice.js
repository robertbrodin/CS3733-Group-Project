var choiceUUID;

function init() {
    selectChoice();
}

function selectChoice() {
    choiceUUID = document.location.search.replace(/^.*?\=/, "");
    getChoiceInfo();
}

function getChoiceInfo() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", choice_get_url + "/" + choiceUUID, true);
   xhr.send();
   
  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      processChoiceResponse(xhr.responseText);
    } else {
        console.log("NO INFORMATION RECIEVED!!!");
        window.location = "./404.html"; 
    }
  };
}

function processChoiceResponse(result) {
    let choice = JSON.parse(result);
    let uuid = choice["uuid"];
    if(uuid == -1) {
        window.location = "./404.html"; 
    } else {
        document.getElementById("choiceUUID").innerHTML = "UUID: " + uuid;
        document.getElementById("choiceDescription").innerHTML = choice["description"];
    
        for(let i = 1; i < 6; i++) {
            let alternative = choice["alternative" + i];
            if(alternative != undefined) {
                loadAlt(alternative, i);
            } else {
                document.getElementById("alternative" + i).remove();
            }
        }
    }
    
}

document.addEventListener("DOMContentLoaded", function() {
    init();
});
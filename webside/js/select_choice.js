function init() {
    selectChoice();
}

function selectChoice() {
    const btn = document.getElementById("selectChoiceBtn");
    
    btn.addEventListener("click", function() {

        var uuid = document.getElementById("choiceID").value;
        onGetChoice(uuid);

    });
    
    
    const btn3 = document.getElementById("createChoiceBtn");
    btn3.addEventListener("click", onAddChoice);
        
}

document.addEventListener("DOMContentLoaded", function() {
    init();
});
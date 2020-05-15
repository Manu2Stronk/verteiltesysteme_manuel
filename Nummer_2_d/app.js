/*
Script um die in Nummer 2c entwickelte App zu verwenden
*/

//Konstante die dann in der Funktion rootAnzeigen genutzt wird
const startURL= "http://localhost:8080/api";

window.addEventListener("load", () => {
    document.getElementById("API").addEventListener("click", function(){
        rootAnzeigen();
    });
    document.getElementById("allePatienten").addEventListener("click", function() {
        allePatientenAnzeigen();
    });
    document.getElementById("alleStationen").addEventListener("click", function() {
        alleStationenAnzeigen();
    });
    document.getElementById("einPatient").addEventListener("click", function() {
        einenPatientAnzeigen();
    });
    document.getElementById("eineStation").addEventListener("click", function() {
        eineStationAnzeigen();
    });
    document.getElementById("clear").addEventListener("click", function() {
        clear();
    });
});

//Zeigt die API im Ausgabe "Pre" an 
async function rootAnzeigen(){
    let response = await fetch(startURL);
    let entries  = await response.json();
    document.getElementById("ausgabe").innerHTML = JSON.stringify(entries, null, 3);
    console.log(entries);
    return entries; 
}

async function allePatientenAnzeigen(){
    let root = await rootAnzeigen();
    let patientURL = (root["_links"]["patients"]);
    let response = await fetch(patientURL.href);
    let entries  = await response.json();
    
    document.getElementById("ausgabe").innerHTML = JSON.stringify(entries, null, 3);
    console.log(entries);
    return entries;
}

async function alleStationenAnzeigen(){
    let root = await rootAnzeigen();
    let stationURL = (root["_links"]["stations"]);
    let response = await fetch(stationURL.href);
    let entries  = await response.json();
    
    document.getElementById("ausgabe").innerHTML = JSON.stringify(entries, null, 3);
    console.log(entries);
    return entries;
}

async function einenPatientAnzeigen(){
    let allePatienten = await allePatientenAnzeigen();
    let patientenArray = allePatienten["_embedded"]["patients"];
    let ersterPatient = patientenArray[0];
    let response = await fetch(ersterPatient["_links"]["self"].href);
    let entries  = await response.json();

    document.getElementById("ausgabe").innerHTML = JSON.stringify(entries, null, 3);
    console.log(entries);
    return entries;
}

async function eineStationAnzeigen(){
    let alleStationen = await alleStationenAnzeigen();
    let stationenArray = alleStationen["_embedded"]["stations"];
    let ersteStation = stationenArray[0];
    let response = await fetch(ersteStation["_links"]["self"].href);
    let entries  = await response.json();

    document.getElementById("ausgabe").innerHTML = JSON.stringify(entries, null, 3);
    console.log(entries);
    return entries;
}

function clear(){
    document.getElementById("ausgabe").innerHTML = null;
}


'use strict';

// let changeColor = document.getElementById('changeColor');

// chrome.storage.sync.get('color', function(data) {
//   changeColor.style.backgroundColor = data.color;
//   changeColor.setAttribute('value', data.color);
// });

// changeColor.onclick = function(element) {
//   let color = element.target.value;
//   chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
//     chrome.tabs.executeScript(
//         tabs[0].id,
//         {code: 'document.body.style.backgroundColor = "' + color + '";'});
//   });
// };
let header = document.getElementById("header");
let imgText = document.getElementById("resource-img-text");
let img = document.getElementById("resource-img");
let name = document.getElementById("resource-name");
let desc = document.getElementById("resource-desc");
let dur = document.getElementById("resource-dur");
let site = document.getElementById("resource-site-name");
let link = document.getElementById("resource-link");
let authors = document.getElementById("resource-authors");
let json = document.getElementById("json");
// let tags = document.getElementById("resource-tags");


chrome.runtime.onMessage.addListener((req, sender, sendResponse) => {
  console.log(req[0]);
  let request = req[0];
  console.log(request.img);
  if(request.img === undefined) {
    img.hidden = true;
    imgText.classList.remove("hidden");
  }

  if(request.status) {
    header.textContent = "Found! Verify and Press Send to Confirm";
    img.hidden = false;
    imgText.classList.add("hidden");
    imgText.value = request.img;
    img.src = request.img;
    name.value = request.title;
    desc.value = request.desc;
    dur.value = request.dur;
    site.value = request.site;
    link.value = request.link;
    authors.value = request.authors.reduce((value, current) => {
      return value += " & " + current;
    })

    json.value = req.map(obj => JSON.stringify(obj));
  } else {
    header.textContent = "Nothing here... See guide or enter manually";
  }

});


chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
  chrome.tabs.sendMessage(tabs[0].id, {message : "this is the message"});
});

// let templateData = {
//   title : "",
//   desc : "",
//   dur : "",
//   authors : [],
//   site : "",
//   link : "",
//   tags : ""
// }
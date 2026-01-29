const UDEMY = 'udemy.com';
const COURSERA = 'coursera.org';
const YOUTUBE = 'youtube.com';
const _VIDEO = 'videos';

let templateData = [{
    status : true,
    type: "",
    title : "",
    desc : "",
    dur : "",
    authors : [],
    site : "",
    link : "",
    tags : "",
    img : "",
    videos : []
}]

let playlist = {

}

chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    let site = window.location.href;
    let data = [...templateData];
    data[0].status = true;
    console.log("0");
    
    if(site.includes(UDEMY) && !(site.includes("/learn/"))) {
        data[0].img = document.getElementsByClassName("styles--introduction-asset__img--9iitL")[0].getAttribute('src');
        data[0].title = document.getElementsByClassName("clp-lead__title")[0].textContent;
        data[0].desc = document.getElementsByClassName("clp-lead__headline")[0].textContent;
        data[0].dur = document.getElementsByClassName("curriculum-header-length")[0].textContent;
        data[0].authors = [...document.getElementsByClassName("instructor--title__link--1NJ6S")].map(item => {
            return item.textContent;
        }); //spread HTML Collection into an array. Dumb

        data[0].site = "Udemy";
        data[0].link = site;
        // data.tags = ;

    } else if(site.includes(COURSERA) && site.includes("/learn/")) {
        // data.img = document.getElementsByClassName("")[0].getAttribute('src');
        data[0].title = document.getElementsByClassName("BannerTitle")[0].firstChild.textContent;
        data[0].desc = document.getElementsByClassName("about-section")[0].getElementsByTagName('p')[0].textContent;
        // data.dur = document.getElementsByClassName("curriculum-header-length")[0].textContent;
        data[0].authors = [...document.getElementsByClassName("instructorWrapper_jfe7wu")].map(item => {
            return item.getElementsByTagName('a')[1].textContent;
        }); //spread HTML Collection into an array. Dumb

        data[0].site = "Coursera";
        data[0].link = site;
        // data.tags = ;
    } else if(site.includes(YOUTUBE)) {
        if(site.includes(_VIDEO)) {


            let childs = document.getElementById("items");
            var arr = Object.keys(childs).map(function (key) { return childs[key]; });

            arr.map((child, index) => {
                data[index].type = 'video';
                data[index].title = child.getElementsById("video-title")[0].innerHTML;
                data[index].img = child.getElementsById("img")[0].getAttribute("src");
                data[index].dur = child.getElementsByClassName("ytd-thumbnail-overlay-time-status-renderer")[0].textContent;
                console.log("2");
                data[index].authors = child.getElementsByClassName("ytd-channel-name").textContent;
        });

            
        }

        
    } else {
        data[0].status = false;
    }
    console.log("4");
    chrome.runtime.sendMessage(data);
    
});
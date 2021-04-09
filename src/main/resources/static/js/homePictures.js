// let images = ['header_01.jpg','header_02.jpg','header_03.jpg'],
//     index = 0, // starting index
//     maxImages = images.length - 1;
// let timer = setInterval(function() {
//     var curImage = images[index];
//     index = (index == maxImages) ? 0 : ++index;
//     // set your image using the curImageVar
//     $('div.mystuff img').attr('src','images/'+curImage);
let images = document.getElementsByName("img"),
    index = 0,
    maxImages = images.length - 1;
console.log(images[0])
for (const image of images) {
    image.style.display = "none";
}
images[2].style.display = "inline";
let timer = setInterval(function () {
    if (index === 3) {
        index = 0;
    }
    if (index === 0) {
        images[2].style.display = "none";
    } else {
        images[index-1].style.display = "none";
    }
    let curImage = images[index];
    console.log(curImage)
    curImage.style.display = "inline";
    index++;
}, 10000);

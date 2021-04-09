

window.onload = function () {
    sortArr();

}

let button = document.getElementById("addProduct");
button.addEventListener("mousedown",function(){
    let productList = document.getElementById("productList");
    let products = document.getElementById("products");

    products.value = "sdasa"

    // if (products.value === "") {
    //         products.value = productList.value;
    //     } else {
    //         products.value = products.value + "," + productList.value;
    //     }
})
window.onclick = function (e) {




    // let products = e.target.parentNode;
    // if (products.id === "productsList") {
    //     let currProduct = e.target;
    //     let input = document.getElementById("products");
    //     input.innerHTML = "hello";
        // if (input.value === "") {
        //     input.value = currProduct.value;
        // } else {
        //     input.value = input.value + "," + currProduct.value;
        // }




        // let selectedProducts = document.getElementById("selectedProducts");
        // let newProduct = document.createElement("input");
        //
        // newProduct.value = currProduct.value;
        // newProduct.innerHTML = currProduct.innerHTML;
        // newProduct.style.backgroundColor = "orange";
        // newProduct.style.fontSize = "20";
        //
        // selectedProducts.appendChild(newProduct);
        // products.removeChild(currProduct);
        //
        // sortArr(selectedProducts);

    // }

    // let productList = e.target.parentNode;
    // if (productList.id === "selectedProducts") {
    //     let removeProduct = e.target;
    //
    //     let input = document.getElementById("products");
    //     input.value = "hello";

        // productList.removeChild(removeProduct);
        // let newProduct = document.createElement("option");
        // newProduct.value = removeProduct.value;
        // newProduct.innerHTML = removeProduct.value;
        //
        // let products = document.getElementById("productsList")
        //     products.appendChild(newProduct);
        // sortArr(products);
    // }

    // let submit = e.target;
    // if (submit.id === "submit") {
    //     let selectedProducts = document.getElementById("selectedProducts");
    //     let input = document.getElementById("products");
    //     for (const selectedProduct of selectedProducts) {
    //         input.value = input.value + "," + selectedProduct;
    //     }
    //
    // }


}


function sortArr(arrToSort) {

    let arrTexts = [];

    for (i = 0; i < arrToSort.length; i++) {
        arrTexts[i] = arrToSort.options[i].text;
    }

    arrTexts.sort();

    for (i = 0; i < arrToSort.length; i++) {
        arrToSort.options[i].text = arrTexts[i];
        arrToSort.options[i].value = arrTexts[i];
    }
}


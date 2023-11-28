function searchKeyword() {

    let sKeyword = [];
    sKeyword = document.getElementsByName("searchInput");

    let keyword ='';
    for (let sKeywordElement of sKeyword) {

        keyword+= sKeywordElement.value

    }

    location.href = "/post/list/search/?keyword=" +keyword+"&page=1";
}
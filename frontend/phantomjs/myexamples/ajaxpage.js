var page = require('webpage').create();
page.open('http://toutiao.com/news_finance/', function(status) {
    console.log("Status: " + status);
    if(status === "success") {
        //page.render('example.png');
        console.debug("page:", page);
    }
    var title = page.evaluate(function() {
        return document.title;
    });
    console.debug("title:", title)

    var main = page.evaluate(function() {
        //return document.getElementByClassName("index-main");
    });
    console.debug("index-main:", JSON.stringify(main))

    page.includeJs("/usr/local/klzhong/lib/js/jquery-1.12.3.min.js", function() {
        page.evaluate(function() {
            console.log("$(\"#pagelet-nfeedlist\").text() -> " + $("#pagelet-nfeedlist").text());
        });
        phantom.exit();
    });

});

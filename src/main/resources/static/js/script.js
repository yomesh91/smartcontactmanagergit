console.log("this is script file");

const toggleSidebar = () => {
if ($(".sidebar").is(":visible")) {
    $(".sidebar").css("display", "none");
    $(".section").css("margin-left", "0%");
}else{
    $(".sidebar").css("display", "block");
    $(".section").css("margin-left", "20%");
}
};
package com.example.realtimetokensystemapp.Constant;

public class ConstantUrls {
    public static String mainUrl = "http://3.19.59.112:8080/api/";
    public static String login = mainUrl + "auth/signin";
    public static String SignUp = mainUrl + "auth/signup";
    public static String createhost = mainUrl + "createhost";
    public static String joinhost = mainUrl + "joinhost";
    public static String updatehost = mainUrl + "updatehost";
    public static String myhosts = mainUrl + "myhosts";

    //    {
//        "pause":false,
//            "updatelimit": 96,
//            "hostID":"B32APTUM"
//    }
    public static String searchhost = mainUrl + "searchhost";
    //    {
//        "query":"doc4"
//    }
    public static String poptop = mainUrl + "poptop";
    //    {
//        "hostID":"doc4"
//    }
    public static String tokensync = mainUrl + "tokensync";
//    {
//        "token":"df14336a",
//            "deviceID":"dfdf96"
//    }

    public static String enrolledhosts = mainUrl + "enrolledhosts";
    public static String logout = mainUrl + "logout";

//    only token

    public static String addSalesExecutive = mainUrl + "SalesExecutive/Add";
    public static String editSalesExecutive = mainUrl + "SalesExecutive/Edit";
    public static String deleteSalesExecutive = mainUrl + "SalesExecutive/Delete";
    public static String productList = mainUrl + "Product/ProductList";
    public static String profile = mainUrl + "Auth/isUser";
    public static String showStockist = mainUrl + "Stockist/list";
    public static String addStockist = mainUrl + "Stockist/add";
    public static String editStockist = mainUrl + "Stockist/edit";
    public static String deleteStockist = mainUrl + "Stockist/Delete";
    public static String showSalesPerson = mainUrl + "SalesPersons/list";
    public static String addSalesPerson = mainUrl + "SalesPersons/add";
    public static String editSalesPerson = mainUrl + "SalesPersons/edit";
    public static String deleteSalesPerson = mainUrl + "SalesPersons/delete";
    public static String showRetailer = mainUrl + "Retailer/list";
    public static String addRetailer = mainUrl + "Retailer/add";
    public static String editRetailer = mainUrl + "Retailer/edit";
    public static String deleteRetailer = mainUrl + "Retailer/delete";
    public static String addScratchCard = mainUrl + "ScratchCard/Add";
    public static String listScratchCard = mainUrl + "ScratchCard/list";
    public static String CONTENT_TYPE = "application/json";
    public static String CONTENT_TYPE2 = "application/json; charset=utf-8";


    public static String banners_image = "http://142.93.220.120/Prevego/uploads/banner/";
    public static String product_image = "http://142.93.220.120/Prevego/uploads/product/";
    public static String profile_image = "http://142.93.220.120/Prevego/uploads/distributor/";
    public static String stockist_image = "http://142.93.220.120/Prevego/uploads/stockist/";
    public static String retailer_image = "http://142.93.220.120/Prevego/uploads/retailer/";

    public static final String token = "83b0e22ab008c511f88bc99716f9ab962ed5b99d241e01f89995e9b7981e54ece1b43aa3aa2acf0b48e74f63811513c3364a4af59f0ddfd6116a3b4115e15327";

    public static final String Username = "Username";
    public static final String Password = "Password";

    public static final String ImageFolder = "Images/";
}

package dbStepDefinitions;

import io.cucumber.java.en.Given;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StepDefinitions {


    String url="jdbc:sqlserver://184.168.194.58:1433;databaseName=hotelmycamp ; user=techproed;password=P2s@rt65";
    String username="techproed";
    String password="P2s@rt65";
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @Given("kullanici HMC veri tabanina baglanir")
    public void kullanici_hmc_veri_tabanina_baglanir() throws SQLException {
        //databesye baglantı kuruyoruz
        connection= DriverManager.getConnection(url,username,password);
        statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


    }
    @Given("kullanici {string} tablosundaki {string} verilerini alir")
    public void kullanici_tablosundaki_verilerini_alir(String table, String field) throws SQLException {
        //Query calsıtırıyoruz
        //SELECT Price FROM tHOTELROOM
        String readQuery="SELECT "+field+" FROM "+table; //
        resultSet=statement.executeQuery(readQuery);

    }
    @Given("kullanici {string} sutunundaki verileri okur")
    public void kullanici_sutunundaki_verileri_okur(String field) throws SQLException {

        //resultSet bzizim suana kadar kullandigimiz Set yapısında degildir
        //resultSet iterator ile calisir

        //resultSet teki bilgileri okumak istiyorsak
        //once iteratori istedigimiz  yere gonderebiliriz

        resultSet.first(); // bu komut iteratoru ilk elemente goturur. gitti ise true, gidemezse false doner
        //iterator istenen konuma gidince artik elementi yazdırabılırız


        System.out.println(resultSet.getString(field));

        //ikinci oda fiyatini gormek istersek
        //iteratoru yollamamiz lazim

        resultSet.next();
        System.out.println(resultSet.getString(field));

        System.out.println(resultSet.next()); //true
        System.out.println(resultSet.getString(field));//resultSet.next() nerde olursa olsun iterator bir sonrakıne gider

        //tum prıce sutununu yazdirmak istersek

        System.out.println("===========================");
        // resultSet ile calisirken iterator konumunu kontrol etmek zorundayız
        //eger 1.elemana donmeden listeyı yazdırmaya devam edersem
        //kaldıgı yerden devam edip 4.element ve sonrakini yazdırır.

        resultSet.absolute(0);
        while (resultSet.next()){
            System.out.println(resultSet.getString(field));
        }

        //price sutununda kac data oldugunu gorelım
        //while loop ile resultSet.next() false donunceye kadar gittik
        //dolayısıyla artik iterator sonda

        resultSet.last();
        System.out.println(resultSet.getRow());

        //suan tum prıce bilgileri resultSet uzerınde
        //eger bu bilgilerle birden fazla test yapacaksak
        //bu bilgileri Java ortamına almakta fayda var
        //ornegın bir List<Double> olusturup
        //tum price verilerini bu listeye ekleyebiliriz
        //boylece bir java objesi olan List sayesinde
        //price uzerinde istedigimiz testleri yapabiliriz

        resultSet.absolute(0);
        List<Double> priceList=new ArrayList<>();

        while (resultSet.next()){
            priceList.add(resultSet.getDouble(field));
        }
        System.out.println(priceList);
    }



}

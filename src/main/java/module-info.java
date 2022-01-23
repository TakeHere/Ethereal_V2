module fr.takehere.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens fr.takehere.ethereal to javafx.fxml;
    exports fr.takehere.ethereal;
    exports fr.takehere.ethereal.example;
    opens fr.takehere.ethereal.example to javafx.fxml;
}
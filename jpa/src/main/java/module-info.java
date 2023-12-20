module jpa{
    exports mainclass;
    exports crud;
    exports classes;
    exports util;
    requires org.hibernate.orm.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    opens classes;

}
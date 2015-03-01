package com.epam.star;


import java.sql.SQLException;
import java.text.ParseException;
import java.util.Random;

public class Main {

    private static Random rnd = new Random();


    public static void main(String[] args) throws SQLException, ParseException {

        Class3 cl = new Class3();
        cl.getField3();

    }

    public static class Class1 {
        private String field1;

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }
    }

    public static class Class2 extends Class1{
        private String field2;

        public String getField2() {
            return field2;
        }

        public void setField1(String field2) {
            this.field2 = field2;
        }
    }

    public static class Class3 extends Class2{
        private String field3;

        public String getField3() {
            return field3;
        }

        public void setField1(String field3) {
            this.field3 = field3;
        }
    }
}

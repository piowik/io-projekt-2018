package io.almp.flatmanager;

class Utils {
    public static boolean isValidEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String nameParser(String name){
        if(name.length()>0) {
            String nameAndFirstLetterOfSurname[] = name.split(" ");
            //String ePattern = "[a-zA-Z_0-9]*[ ]\\.";
            //java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            return nameAndFirstLetterOfSurname.length>1 ? nameAndFirstLetterOfSurname[0] +" "+ nameAndFirstLetterOfSurname[1].charAt(0)+"." : nameAndFirstLetterOfSurname[0];
        }else
            return "";

    }
}

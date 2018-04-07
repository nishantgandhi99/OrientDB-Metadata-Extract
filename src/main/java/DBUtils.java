
public class DBUtils {
    public static String type(String num) {
        switch (num) {
            case "0":
                return "Boolean";
            case "1":
                return "Integer";
            case "2":
                return "Short";
            case "3":
                return "Long";
            case "4":
                return "Float";
            case "5":
                return "Double";
            case "6":
                return "Datetime";
            case "7":
                return "String";
            case "8":
                return "Binary";
            case "9":
                return "Embedded";
            case "10":
                return "Embedded list";
            case "11":
                return "Embedded set";
            case "12":
                return "Embedded map";
            case "13":
                return "Link";
            case "14":
                return "Link list";
            case "15":
                return "Link set";
            case "16":
                return "Link map";
            case "17":
                return "Byte";
            case "18":
                return "Transient";
            case "19":
                return "Date";
            case "20":
                return "Custom";
            case "21":
                return "Decimal";
            case "22":
                return "LinkBag";
            default:
                return "Any";

        }
    }
}

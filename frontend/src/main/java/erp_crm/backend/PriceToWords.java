package main.java.erp_crm.backend;



public class PriceToWords {
    static String[] jedn = {"", "jeden", "dwa", "trzy", "cztery", "pięć", "sześć","siedem", "osiem","dziewięć"};
    static String[] nast = {"", "jedenaście", "dwanaście", "trzynaście","czternaście","piętnaście","szesnaście","siedemnaście","osiemnaście","dziewiętnaście"};
    static String[] dzies = {"", "dziesięć","dwadzieścia","trzydzieści","czterdzieści","pięćdziesiąt","szcześćdziesiąt","siedemdziesiąt","osiemdziesiąt","dziewięćdziesiąt"};
    static String[] set = {"", "sto", "dwieście","trzysta","czterysta","pięćset","sześćset","siedemset","osiemset","dziewięćset"};
    static String[][] tys = {
            {"","",""},
            {"tysiąc","tysiące","tysięcy"},
            {"milion","miliony","milionów"}};


    public static String toWords(Float cena){
        String gr="";
        String zl="";
        Integer c = (int)Math.floor(cena);
        Integer u = Math.round((cena-c)*100);
        zl = getZlote(zl, c);
        gr = getGrosze(gr,u);
        return buildString(c)+zl+","+ buildString(u)+gr;
    }

    private static String getGrosze(String gr, Integer u) {
        if(u==0) gr="groszy";
        else if(u==1) gr="grosz";
        else if(u>1 && u<5) gr="grosze";
        else if(u>=5 && u<22) gr="groszy";
        else if(u>=22) {
            switch(u%10){
                case 0:
                    gr="groszy";
                    break;
                case 1:
                    gr="groszy";
                    break;
                case 2:
                    gr="grosze";
                    break;
                case 3:
                    gr="grosze";
                    break;
                case 4:
                    gr="grosze";
                    break;
                case 5:
                    gr="groszy";
                    break;
                case 6:
                    gr="groszy";
                    break;
                case 7:
                    gr="groszy";
                    break;
                case 8:
                    gr="groszy";
                    break;
                case 9:
                    gr="groszy";
                    break;
            }
        }
        return gr;
    }

    private static String getZlote(String zl, Integer c) {
        if(c==0) zl="złotych";
        else if(c==1) zl="złoty";
        else if(c>1 && c<5) zl="złote";
        else if(c>=5 && c<22) zl="złotych";
        else if(c>=22) {
            switch(c%10){
                case 0:
                    zl="złotych";
                    break;
                case 1:
                    zl="złotych";
                    break;
                case 2:
                    zl="złote";
                    break;
                case 3:
                    zl="złote";
                    break;
                case 4:
                    zl="złote";
                    break;
                case 5:
                    zl="złotych";
                    break;
                case 6:
                    zl="złotych";
                    break;
                case 7:
                    zl="złotych";
                    break;
                case 8:
                    zl="złotych";
                    break;
                case 9:
                    zl="złotych";
                    break;
            }
        }
        return zl;
    }

    private static String buildString(int c){
        int s,d,n,j,k,g=0;
        String st="";
        if(c==0){
            return "zero ";
        }

        while(c!=0){
            s=(c%1000)/100;
            d=(c%100)/10;
            j=c%10;

            if(d==1 && j>0){
                n=j;
                d=0;
                j=0;
            } else n=0;

            if (j == 1 & s + d + n == 0) {
                k = 0;

                if (s + d == 0 && g > 0){
                    j = 0;
                    st = tys[g][k] + st;
                }
            } else if (j == 2) {
                k = 1;
            } else if (j == 3) {
                k = 1;
            } else if (j == 4) {
                k = 1;
            } else {
                k = 2;
            }
            if (s+d+n+j > 0) {
                st = " "+set[s] +" "+ dzies[d] +" "+ nast[n]
                        +" "+ jedn[j] +" "+ tys[g][k]+ st;
            }
            c/=1000;
            g++;
        }

        return st;
    }
}

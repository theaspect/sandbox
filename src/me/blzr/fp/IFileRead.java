package me.blzr.fp;

public class IFileRead {
    public static void main(String[] args) {
        String f = "file.txt";
        // Найти строки длиннее 2 символов и преобразовать в верхний регистр
    }

    //<editor-fold desc="FilesLinesPath">
    /*
    public static void main1(String[] args) {
        String f = "file.txt";
        BufferedReader br = null;
        List<String> results = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 2) {
                    results.add(line.toUpperCase());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    //Do nothing
                }
            }
        }

        print(results);
    }

    public static void main2(String[] args) {
        String f = "file.txt";
        try (Stream<String> lines = Files.lines(Paths.get(f))) {
            lines
                    .filter(l -> l.length() > 2)
                    .map(String::toUpperCase)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    //</editor-fold>
}

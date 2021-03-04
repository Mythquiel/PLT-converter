import java.io.*;
import java.util.Base64;

public class Decryption {

    String readLine, labelString, invoiceString;
    File folder;
    int lineNumber = 1, fileNumber = 1;

    public void plt(String path) {

        folder = new File(path);
        File[] fileList = folder.listFiles();

        for (File file : fileList) {
            if (file.isFile() && file.getName().endsWith(".xml")) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    while ((readLine = reader.readLine()) != null) {
                        if (lineNumber == 16) {
                            labelString = readLine.trim();
                            lineNumber++;
                        } else if (lineNumber == 19) {
                            invoiceString = readLine.trim();
                            lineNumber++;
                        } else {
                            lineNumber++;
                        }
                    }

                    labelString = labelString.substring(47, labelString.length() - 85);
                    invoiceString = invoiceString.substring(47, invoiceString.length() - 85);

                    File decodedLabel = new File(path + "\\output\\" + "label" + fileNumber + ".pdf");
                    byte[] encodedLabelBytes = labelString.getBytes();
                    byte[] decodedLabelBytes = Base64.getDecoder().decode(encodedLabelBytes);
                    OutputStream outputLabelStream = new FileOutputStream(decodedLabel);
                    outputLabelStream.write(decodedLabelBytes);

                    File decodedInvoice = new File(path + "\\output\\" + "invoice" + fileNumber + ".pdf");
                    byte[] encodedInvoiceBytes = invoiceString.getBytes();
                    byte[] decodedInvoiceBytes = Base64.getDecoder().decode(encodedInvoiceBytes);
                    OutputStream outputInvoiceStream = new FileOutputStream(decodedInvoice);
                    outputInvoiceStream.write(decodedInvoiceBytes);

                    fileNumber++;
                    lineNumber = 1;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

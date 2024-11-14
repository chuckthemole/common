package com.rumpus.common.OCR;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.util.Language;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OcrTesseract extends AbstractCommonObject {

    public static final String PATH_TO_DATA = "src/main/resources/tessdata";
    public static final String DEFAULT_LANGUAGE = Language.LanguageCode.ENGLISH.getLanguage(); // TODO: this seems like a lot to do just to set the language

    private OcrTesseract() {}

    /**
     * Perform OCR on an image file. TODO: overload this method to accept a language code and other params
     * 
     * @param pathToFile the path to the image file
     * @return the text extracted from the image
     */
    public static String doOCR(String pathToFile) {
        java.io.File imageFile = new java.io.File(pathToFile);
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(OcrTesseract.PATH_TO_DATA);
        tesseract.setLanguage(OcrTesseract.DEFAULT_LANGUAGE);
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        try {
            return tesseract.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}

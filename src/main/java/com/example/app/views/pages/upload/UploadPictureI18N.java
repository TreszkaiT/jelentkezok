package com.example.app.views.pages.upload;

import com.vaadin.flow.component.upload.UploadI18N;

import java.util.Arrays;

/**
 * Provides a default I18N configuration for the Upload examples
 * <p>
 * At the moment the Upload component requires a fully configured I18N
 * instance, even for use-cases where you only want to change individual texts.
 * <p>
 * This I18N configuration is an adaption of the web components I18N defaults
 * and can be used as a basis for customizing individual texts.
 */
public class UploadPictureI18N extends UploadI18N {
    public UploadPictureI18N() {
        setDropFiles(new DropFiles()
                .setOne("A fájlt csak húzd ide")
                .setMany("A fájlokat csak húzd ide"));
        setAddFiles(new AddFiles()
                .setOne("Fájl feltöltése...")
                .setMany("Fájlok feltöltése..."));
        setCancel("Mégsem");
        setError(new Error()
                .setTooManyFiles("Túl sok fájl.")
                .setFileIsTooBig("A fájl mérete túl nagy.")
                .setIncorrectFileType("Nem jó a fájl típusa."));
        setUploading(new Uploading()
                .setStatus(new Uploading.Status()
                        .setConnecting("Kapcsolódás...")
                        .setStalled("Elakadt")
                        .setProcessing("Fájl feldolgozás alatt...")
                        .setHeld("Sorban"))
                .setRemainingTime(new Uploading.RemainingTime()
                        .setPrefix("hátralévő idő: ")
                        .setUnknown("a hátralévő idő ismeretlen"))
                .setError(new Uploading.Error()
                        .setServerUnavailable("Sikertelen feltöltés, kérlek próbáld meg később")
                        .setUnexpectedServerError("A feltöltés szerverhiba miatt nem sikerült")
                        .setForbidden("Feltölteni tilos")));
        setUnits(new Units()
                .setSize(Arrays.asList("B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")));
    }
}
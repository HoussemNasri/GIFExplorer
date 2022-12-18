package tech.houssemnasri.gifx.parser;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public final class ApplicationExtension implements GIFBlock {
        /*
         * Sequence of eight printable ASCII characters used to identify the application owning the Application Extension.
         * */
        private final String applicationId;
        /*
         * Sequence of three bytes used to authenticate the Application Identifier. An Application program may use an
         * algorithm to compute a binary code that uniquely identifies it as the application owning
         * the Application Extension.
         * */
        private final int[] applicationAuthenticationCode;

        SubBlocks applicationDataSubBlocks;


    public ApplicationExtension(String applicationId, int[] applicationAuthenticationCode) {
        assert applicationId != null && applicationId.length() == 8;
        assert applicationAuthenticationCode != null && applicationAuthenticationCode.length == 3;
        this.applicationId = applicationId;
        this.applicationAuthenticationCode = applicationAuthenticationCode;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public int[] getApplicationAuthenticationCode() {
        return applicationAuthenticationCode;
    }

    public void setApplicationDataSubBlocks(SubBlocks applicationDataSubBlocks) {
        Objects.requireNonNull(applicationDataSubBlocks);
        this.applicationDataSubBlocks = applicationDataSubBlocks;
    }

    public Optional<SubBlocks> getApplicationDataSubBlocks() {
        return Optional.ofNullable(applicationDataSubBlocks);
    }

    @Override
    public String toString() {
        return "ApplicationExtension{" +
                "applicationId='" + applicationId + '\'' +
                ", applicationAuthenticationCode=" + Arrays.stream(applicationAuthenticationCode).mapToObj(i -> "0x" + Integer.toHexString(i).toUpperCase()).toList() +
                ", applicationDataSubBlocks=" + applicationDataSubBlocks +
                '}';
    }
}

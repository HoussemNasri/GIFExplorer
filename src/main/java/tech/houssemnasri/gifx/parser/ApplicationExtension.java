package tech.houssemnasri.gifx.parser;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ApplicationExtension implements GIFBlock {
    /*
     * Sequence of eight printable ASCII characters used to identify the application owning the Application Extension.
     * */
    private final String applicationId;
    private final AuthCode authCode;
    SubBlocks applicationDataSubBlocks;

    public ApplicationExtension(String applicationId, AuthCode authCode) {
        assert applicationId != null && applicationId.length() == 8;
        this.applicationId = applicationId;
        this.authCode = authCode;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public AuthCode getAuthCode() {
        return authCode;
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
                ", applicationAuthenticationCode=" + authCode.asASCII() +
                ", applicationDataSubBlocks=" + applicationDataSubBlocks +
                '}';
    }

    /*
     * Sequence of three bytes used to authenticate the Application Identifier. An Application program may use an
     * algorithm to compute a binary code that uniquely identifies it as the application owning
     * the Application Extension.
     * */
    public record AuthCode(Integer[] authCode) {
        public AuthCode {
            assert authCode != null && authCode.length == 3;
        }

        AuthCode(int[] authCode) {
            this(Arrays.stream(authCode).boxed().toArray(Integer[]::new));
        }

        public String asASCII() {
            return Arrays.stream(authCode)
                         .map(byt -> (char) byt.intValue())
                         .map(String::valueOf)
                         .collect(Collectors.joining());
        }

        public Integer[] getContent() {
            return authCode;
        }
    }
}

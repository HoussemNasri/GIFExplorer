package tech.houssemnasri.gifx.parser;

public record GraphicControlExtension(
        /*
         Indicates whether a transparency index is given in the Transparent Index field.
         * */
        boolean hasTransparentColor,
        /*
          Indicates whether user input is
          expected before continuing. If the flag is set, processing will
          continue when user input is entered. The nature of the User input
          is determined by the application (Carriage Return, Mouse Button
          Click, etc.).
        * */
        boolean shouldWaitForUserInput,
        /* Indicates the way in which the graphic is to be treated after being displayed. */
        GraphicDisposalMethod disposalMethod,
        int delayTime,
        /*
          The Transparency Index is such that when
          encountered, the corresponding pixel of the display device is not
          modified and processing goes on to the next pixel. The index is
          present if and only if hasTransparentColor is set to true.
         */
        int transparencyIndex
) {
}

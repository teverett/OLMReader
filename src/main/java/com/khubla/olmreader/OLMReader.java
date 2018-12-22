package com.khubla.olmreader;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OLMReader {
   /**
    * file option
    */
   private static final String FILE_OPTION = "file";

   public static void main(String[] args) throws IOException {
      System.out.println("khubla.com OLM Reader");
      /*
       * options
       */
      final Options options = new Options();
      final Option oo = Option.builder().argName(FILE_OPTION).longOpt(FILE_OPTION).type(String.class).hasArg().required(true).desc("file to read").build();
      options.addOption(oo);
      /*
       * parse
       */
      final CommandLineParser parser = new DefaultParser();
      CommandLine cmd = null;
      try {
         cmd = parser.parse(options, args);
         /*
          * get the file
          */
         final String filename = cmd.getOptionValue(FILE_OPTION);
         if (null != filename) {
            /*
             * read to console
             */
            final OLMReaderConsoleReader olmReaderConsoleReader = new OLMReaderConsoleReader();
            olmReaderConsoleReader.read(filename);
         }
      } catch (final Exception e) {
         e.printStackTrace();
         final HelpFormatter formatter = new HelpFormatter();
         formatter.printHelp("posix", options);
      }
   }
}

package com.khubla.olmreader;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.khubla.olmreader.olm.OLMFile;
import com.khubla.olmreader.olm.OLMMessage;
import com.khubla.olmreader.olm.OLMMessageCallback;
import com.khubla.olmreader.olm.OLMRawMessageCallback;

public class OLMReader implements OLMMessageCallback, OLMRawMessageCallback {
   /**
    * file option
    */
   private static final String FILE_OPTION = "file";

   public static void main(String[] args) throws IOException {
      System.out.println("khubla.com kPascal Interpreter");
      /*
       * options
       */
      final Options options = new Options();
      final Option oo = Option.builder().argName(FILE_OPTION).longOpt(FILE_OPTION).type(String.class).hasArg().required(true).desc("file to compile").build();
      options.addOption(oo);
      /*
       * parse
       */
      final CommandLineParser parser = new DefaultParser();
      CommandLine cmd = null;
      try {
         cmd = parser.parse(options, args);
      } catch (final Exception e) {
         e.printStackTrace();
         final HelpFormatter formatter = new HelpFormatter();
         formatter.printHelp("posix", options);
         System.exit(0);
      }
      /*
       * get the file
       */
      final String filename = cmd.getOptionValue(FILE_OPTION);
      if (null != filename) {
         final OLMReader olmReader = new OLMReader();
         OLMFile.readOLMFile(filename, olmReader, olmReader);
      }
   }

   @Override
   public void message(OLMMessage olmMessage) {
      System.out.println(olmMessage.getBody());
   }

   @Override
   public void message(String olmMessage) {
      System.out.println(olmMessage);
   }
}

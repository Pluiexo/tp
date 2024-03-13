package staffconnect.logic.parser;

import static java.util.Objects.requireNonNull;
import static staffconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
// import static staffconnect.logic.parser.CliSyntax.PREFIX_FACULTY; // TODO: add parsing for faculty and module
// import static staffconnect.logic.parser.CliSyntax.PREFIX_MODULE;
import static staffconnect.logic.parser.CliSyntax.PREFIX_TAG;


import staffconnect.commons.exceptions.IllegalValueException;
import staffconnect.logic.commands.FilterCommand;
import staffconnect.logic.parser.exceptions.ParseException;
import staffconnect.model.person.PersonHasTagPredicate;
import staffconnect.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TAG);

        Tag tagName;
        try {
            tagName = ParserUtil.parseTag(argumentMultimap.getValue(PREFIX_TAG).orElse(""));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterCommand.MESSAGE_USAGE), ive);
        }

        return new FilterCommand(new PersonHasTagPredicate(tagName));
    }

}

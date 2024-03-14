package staffconnect.model.meeting;


import static staffconnect.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Meeting event in the staff book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Meeting {

    private final Description description;
    private final MeetDateTime startDate;

    /**
     * Constructs a {@code Meeting}.
     *
     * @param description A valid meeting description.
     */

    public Meeting(Description description, MeetDateTime startDate) {
        requireAllNonNull(description, startDate);
        this.description = description;
        this.startDate = startDate;
    }


    @Override
    public int hashCode() {
        return Objects.hash(description, startDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof staffconnect.model.meeting.Meeting)) {
            return false;
        }

        staffconnect.model.meeting.Meeting otherMeeting = (staffconnect.model.meeting.Meeting) other;
        return description.equals(otherMeeting.description) && startDate.equals(otherMeeting.startDate);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return startDate + ":" + description;
    }

}
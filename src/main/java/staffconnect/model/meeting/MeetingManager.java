package staffconnect.model.meeting;

import static java.util.Objects.requireNonNull;
import static staffconnect.commons.util.CollectionUtil.requireAllNonNull;
import static staffconnect.model.meeting.comparator.MeetingDateComparator.MEETING_DATE_COMPARATOR;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * Represents the in-memory model of the person's meetings
 */
public class MeetingManager {

    public static final Predicate<Meeting> PREDICATE_SHOW_ALL_MEETINGS = unused -> true;

    private final MeetingList meetingList;
    private final FilteredList<Meeting> filteredMeetings;
    private final SortedList<Meeting> sortedFilteredMeetings;

    /**
     * Initializes a MeetingList with the given meeting list.
     */
    public MeetingManager(MeetingList meetingList) {

        requireAllNonNull(meetingList);
        this.meetingList = meetingList;
        filteredMeetings = new FilteredList<>(this.meetingList.asUnmodifiableObservableList());
        sortedFilteredMeetings = new SortedList<>(filteredMeetings);
        updateSortedMeetingList(MEETING_DATE_COMPARATOR); //sets the default view to be sorted by date.
    }

    public MeetingManager() {
        this(new MeetingList());
    }



    //=========== MeetingList ================================================================================

    /**
     * Replaces all the current meeting data with the current list.
     * @param meetingList the input meeting list to replace to.
     */
    public void setMeetingList(List<Meeting> meetingList) {
        this.meetingList.setMeetings(meetingList);
    }

    /**
     * Returns the meeting list as a MeetingList.
     * @return a MeetingList of the unique meetings.
     */
    public MeetingList getMeetings() {
        return meetingList;
    }

    /**
     * Gets an unmodifiable list of meetings.
     * @return an ObservableList of meetings.
     */
    public ObservableList<Meeting> getMeetingList() {
        return meetingList.asUnmodifiableObservableList();
    }

    /**
     * Checks if the current meeting contains the same meeting.
     * @param meeting the meeting to check.
     * @return true if a meeting with the same identity as {@code meeting} exists in the meeting list.
     */
    public boolean hasMeeting(Meeting meeting) {
        requireNonNull(meeting);
        return meetingList.contains(meeting);
    }

    /**
     * Removes the specified meeting form the current meeting list.
     * @param target meeting to remove .
     */
    public void deleteMeeting(Meeting target) {
        meetingList.remove(target);
    }

    /**
     * Adds a meeting to the current list.
     * @param meeting to add to the current meeting list.
     */
    public void addMeeting(Meeting meeting) {
        meetingList.add(meeting);
        updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
    }

    //=========== Filtered Meeting List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code meeting}.
     */
    public ObservableList<Meeting> getFilteredMeetingList() {
        return sortedFilteredMeetings;
    }

    /**
     * Updates the filter of the filtered meeting list to filter by the given {@code predicate}.
     * @param predicate to filter the list to.
     */
    public void updateFilteredMeetingList(Predicate<Meeting> predicate) {
        requireNonNull(predicate);
        filteredMeetings.setPredicate(predicate);
        sortedFilteredMeetings.setComparator(null);
    }

    /**
     * Updates the sort attribute of the sorted meeting list to sort by the given comparator.
     * @param comparator to decide how to sort the meetings.
     */
    public void updateSortedMeetingList(Comparator<Meeting> comparator) {
        requireNonNull(comparator);
        filteredMeetings.setPredicate(null);
        sortedFilteredMeetings.setComparator(comparator);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetingManager)) {
            return false;
        }

        MeetingManager otherModelManager = (MeetingManager) other;
        return meetingList.equals(otherModelManager.meetingList)
                && filteredMeetings.equals(otherModelManager.filteredMeetings);
    }

}
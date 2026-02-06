/**
 * EventStats enumeration represents the outcome state of an activity event
 * See if it actually applied or not
*/

public enum EventStatus
{

    APPLIED, //means that the event was executed successfully
    CANCELLED // means that the event was skipped or cancelled
}



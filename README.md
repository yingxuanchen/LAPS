# LAPS
Leave Application Processing System

This program is still in progress and not completed yet.

This is a school project to use Spring MVC to create webpages for leave processing.
Employees can apply for leaves and managers can approve or reject the leaves.

Business rules
- Any type of leave can be applied at any time of the year, as long as within the year
- Start date and end date of leave application cannot be on weekends or public holidays
- Cannot apply leave when any day in the period clash with other 'applied', 'updated' or 'approved' leave
- Employee can 'cancel' any 'approved' leave. Manager can 'approve' or 'reject' the 'cancellation. If manager 'approve', leave status becomes 'cancelled'. If manager 'reject', leave status remains as 'approved'.
- Employee can 'delete' any 'applied' or 'updated' leave (as manager has not processed the leave). 'Deleted' leave are removed from database.

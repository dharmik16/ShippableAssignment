# GetOpenIssueStats

## Overview

GetOpenIssueStats is a Java based API which gives information regarding the open issues for any public github repository.

## Usage
Input - A link to any public GitHub Repository taken from TextField

Output - A Table displaying - 
  Total number of open issues
  * Number of open issues that were opened in the last 24 hours
  * Number of open issues that were opened more than 24 hours ago but less than 7 days ago
  * Number of open issues that were opened more than 7 days ago 

## Implementation Details
Given the link to github repository the API creates a HTTP connection to the link and gets the content of html document.
The API crawls through the document and finds all the open issue contents from it and adds the time stamp to a linked list.
The process is repeated till there is a next link available to crawl.
Once we have all the time stamp for open issues, the API calculates the duration for the issue opened and 
increments the individual counters accordingly.

## Future Scope
The program can be improved with better GUI and can also be extended to calculate open issues based on
label such as bug, resolved, etc along with duration.


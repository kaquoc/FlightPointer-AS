## FlightPointer-AS
Android studio app


- **Support for Google Pixel 3A (for now)**

## Application idea
FlightPointer provides a simple app that displays the name and location of the aircraft nearest to the user's longitude and lattitude.
Definition of nearest aircraft: nearest aircraft must be an aircraft that has an altitude higher than 0 (not idling on the ground) and must have speed > 10.

All flight data is provided by https://www.adsbexchange.com/ through RapidAPI.

User can entered their desired search radius. (NOT working under current ADSBx Flight Sim Traffic version). Only ADSBx Flight Sim Traffic version 2 (paid plan) works.

Also because I really like aviation and aviation tracking!


## Learning goals
The purpose of creating this app is to:
- Improve upon my OOP Java skills and its real-world application.
- Learn and familiarise with the use of API and HTTP requests
  - OKHTTP3 https://square.github.io/okhttp/
  - Absdbx Flight Tracking API: https://rapidapi.com/adsbx/api/adsbx-flight-sim-traffic/
  - Google Services Map
- Managing and understanding dependancies with Android Studio and Gradle.
  - Depandancies uses: okhttp3, google services location/map, google json. 
- Plan and implement product development process.
- Understand the basics of an Android Application.
- Familiaries with usage of Git and GitHub through an IDE.



## License

```
Copyright 2022 Ka Nguyen

Code is free to use by anyone of the public. 





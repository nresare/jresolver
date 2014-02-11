# jresolver - The java DNS resolver library

This is a domain name resolver library written in pure java. It is what RFC1034
describes as a stub resolver, in other words it uses a single nameserver to
do the hard work of resolving it's queries. At the moment it only handles
queries for MX records, but it can easily be extended to resolve new types of
queries as needed.

## Features

- Lightweight. The binary jar is less than 18k at the moment, and has no
exernal dependencies besides JDK 1.4.
- Multithreaded. It handles multiple concurrent threads sharing a single
Resolver instance.
- Easy to use and develop. The software does one thing, and the code is quite
readable.
- Free software. Released under the GPL 3.0 license, this software can be used,
modified and redistributed by anyone respecting the terms of the license. If you
need other licensing options, please contact me.

## Usage

A snippet of code says more than lots of words: 

  Resolver r = new Resolver("ns.voxbiblia.se");
  List l = r.resolve(new MXQuery("voxbiblia.se"));
  for (int i = 0; i < l.size(); i++) {
    MXRecord mx = (MXRecord)l.get(i);
    System.out.println("mx: " + mx.getExchange() + " p: "+ mx.getPreference());
  }

The build system used is Apache Maven. If your maven is properly installed, you should
be able to run 'mvn verify' to run the automated tests.

## Credits

This software is developed and maintained by Noa Resare with support from
Voxbiblia. Thanks guys for letting me do this! I want to give credit to the
author of dnsjava (http://xbill.org/dnsjava/), a piece of software that I had a
look at before I decided that it would be fun to write my own library with
RFC1035 as a starting point.

## Contact

Feel free to write me with comments, suggestions, bug reports and patches.
Noa Resare (noa@resare.com)

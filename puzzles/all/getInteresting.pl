#!/usr/bin/perl

while(<>) {
	if (/ solvable/) {
		push @list, $_;
	}
}

print "$#list\n";

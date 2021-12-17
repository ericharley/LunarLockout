$dec = bin2dec("1111111111111111111111111");
print $dec,"\n";

	my $binStr;
	my $numOnes;
	my @bits;

for ($i = 0; $i < $dec; $i++) {

	$binStr = dec2bin($i);
	$binStr = "0"x(25-length($binStr)).$binStr;

	$numOnes = 0;
	@bits = split //,$binStr;
	foreach $bit (@bits) {
		if ($bit == 1) {
			$numOnes++;
		}

	}
	next if $numOnes > 6;
	print $binStr,": $numOnes\n";
}

sub bin2dec {
    return unpack("N", pack("B32", substr("0" x 32 . shift, -32)));
}


sub dec2bin {
    my $str = unpack("B32", pack("N", shift));
    $str =~ s/^0+(?=\d)//;   # otherwise you'll get leading zeros
    return $str;
}


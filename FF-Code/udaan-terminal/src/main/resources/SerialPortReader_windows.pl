#! perl -w

use strict;
use Win32::SerialPort;
use Win32API::CommPort;


#for(;;) {
#for(1..10) {
 	
	
	my $readData = &read(1);
	print "$readData\n";
	
#}

sub init() {

}


sub read() {
	
	my $ob = Win32::SerialPort->new ('COM1') || die;

	my $baud = $ob->baudrate(9600) || die 'fail setting baudrate, try -b option'; 
	my $parity = $ob->parity("none") || die 'fail setting parity to none';
	my $data = $ob->databits(8) || die 'fail setting databits to 8'; 
	my $stop = $ob->stopbits(1) || die 'fail setting stopbits to 1';
	my $hshake = $ob->handshake("none") || die 'fail setting handshake to none';
	my $dataType = $ob->datatype('raw') || die 'fail setting datatype raw';
	$ob->write_settings || die 'could not write settings';
	$ob->error_msg(1); # use built-in error messages
	$ob->user_msg(1); # 
	$ob->read_const_time(10); # important for nice behaviour, otherwise hogs cpu
	$ob->read_char_time(10); 
	#$ob->is_read_interval(5000);# dto.
	
	
	my $sum = 0;
	# clear the read buffer variables
	my $readChars = 0; my $readBytes = ""; my $result =0;
		
	# Read serial port data
	my $status = $ob->are_match("OK","ERROR");   
	($readChars, $readBytes) = $ob->read(100);
	#print("after read $status\n ");
	#print "char:  $readChars  and readBytes:  $readBytes\n";
	
	if ($readChars == 0) {
		#print "Unable to connect with serial port\n";
		
		$result = -1;
	} else {
		#print "Machine output: $readBytes\n";
		my @arry = split / /, $readBytes;
		
		if(defined($arry[1])) {
			my $count = 0; my $rec =0;
			for (my $temp = 10; $temp >= 1; $temp--) {
				$rec = $arry[$temp];
				$sum = $sum + $rec;
				$count++;
			}

    		
			my $avg = $sum/$count;
			$result = $avg;
			
		} else {
			$result = -2;
		}
		
	}
	undef $ob; 
	return $result;
}



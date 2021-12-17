while(<>){my($s,$j)=split/:/;@b=split//,$s;foreach$b(@b){if($b eq"1"){$b="2";print @b,"\n";$b="1";}}}

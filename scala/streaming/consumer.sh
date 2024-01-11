#!/bin/sh

case $1 in
	s|stat)
		echo "liczba_ocen,suma_ocen,liczba_osob"
		cat stat/*.csv | sort
		;;
	a|anom)
		echo "analizowany_okres,tytul_filmu,liczbe_ocen,Srednia_ocen"
		cat anom/*.csv | sort
		;;
	*)
		echo "usage: ./consume.sh (anom|stat)"
		exit 1
		;;
esac
#include <fstream>
#include <iostream>
#include <stdio.h>    //printf
#include <string.h>   //strncpy
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <net/if.h>   //ifreq
#include <unistd.h>   //close

const char keyvalue[]= "KSGIV";

#define CODE_TIME 2409

int main(){
	std::ifstream src("myprog.out", std::ios::binary);
	std::ofstream dst("myprog_patched.out",std::ios::binary);
	char buf;
	src.seekg(0,std::ios::beg);
	dst.seekp(0,std::ios::beg);
	int fd;
	struct ifreq ifr;
	char *iface = "wlp2s0";
	unsigned char *mac;

	fd = socket(AF_INET, SOCK_DGRAM, 0);

	ifr.ifr_addr.sa_family = AF_INET;
	strncpy(ifr.ifr_name , iface , IFNAMSIZ-1);
 
	ioctl(fd, SIOCGIFHWADDR, &ifr);
 
	close(fd);

	mac = (unsigned char *)ifr.ifr_hwaddr.sa_data;
	//display mac address
	printf("KEY : %.2x:%.2x:%.2x:%.2x:%.2x:%.2x\n" , keyvalue[0], keyvalue[1], keyvalue[2], keyvalue[3], keyvalue[4], keyvalue[5]);
	int index = 0;
	while(!src.eof()){
		char buf = 0;
		src.get(buf);
		if ((src.tellg() >= CODE_TIME) && (index < (sizeof(keyvalue)-1))){
			dst.put(mac[index++]);
			std::cout<<buf<<"\t"<<mac[index-1]<<std::endl;
		} else {
			dst.put(buf);
		}
	}
}
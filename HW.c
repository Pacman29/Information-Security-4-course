#include <stdio.h>    //printf
#include <string.h>   //strncpy
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <net/if.h>   //ifreq
#include <unistd.h>   //close
#include <string.h>

const char keyvalue[]= "KSGIV";
 
int main()
{
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
    if (memcmp(mac, keyvalue, sizeof(keyvalue)-1)){
        return 1;
    }
    //display mac address
    printf("Mac : %.2x:%.2x:%.2x:%.2x:%.2x:%.2x\n" , mac[0], mac[1], mac[2], mac[3], mac[4], mac[5]);
    printf("KEY : %.2x:%.2x:%.2x:%.2x:%.2x:%.2x\n" , keyvalue[0], keyvalue[1], keyvalue[2], keyvalue[3], keyvalue[4], keyvalue[5]);
    printf("%s\n",keyvalue);
    printf("%s\n",(char *) ( void *) mac);
    return 0;
}
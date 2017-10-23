#include <fstream>
#include <iostream>


const char keyvalue[]= "KSGIV";

int main(){
	std::fstream a("myprog.out", std::ios::in|std::ios::out|std::ios::binary);
	char buf;
	a.seekg(0,std::ios::beg);
	a.seekp(0,std::ios::beg);
	int i = 0;
	std::cout << sizeof(keyvalue)<<std::endl;
	while(!a.eof()){
		a.get(buf);
		//std::cout<<buf;
		if (buf == keyvalue[0]){
			i=1;
		}else{	
			if (buf == keyvalue[i++]){
				if (i >= (sizeof(keyvalue) - 1)){
					std::cout << (int(a.tellg())-4)<<std::endl;
					//std::cout << "DONE";
					i = 0;
				}
			}else{
				i = 0;
			}
		}
	}
	a.close();
}
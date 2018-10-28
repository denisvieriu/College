#ifndef _GENERIC_EXCEPTION_H
#define _GENERIC_EXCEPTION_H

class GenericException : public std::exception {

public:
    GenericException(const char* msg) : std::exception(msg) {}

    /*const char* get_file() const { return file; }
    int get_line() const { return line; }
    const char* get_func() const { return func; }
    const char* get_info() const { return info; }
*/
}

#endif

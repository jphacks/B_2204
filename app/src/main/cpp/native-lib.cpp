#include <jni.h>
#include <string>
#include "uc-jni.hpp"

UC_JNI_DEFINE_JCLASS_ALIAS(YourOwnClass, com/example/YourOwnClass);

jint JNI_OnLoad(JavaVM * vm, void * __unused reserved)
{
    uc::jni::java_vm(vm);

    return JNI_VERSION_1_6;
}

extern "C" {
    JNIEXPORT float JNICALL
    Java_com_example_cardgame_InputActivity_calculateHour(JNIEnv* env, jobject obj,
                                                          jfloat hour, jfloat minute) {
        return hour + (minute / 60);
    }
}

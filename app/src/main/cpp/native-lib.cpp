#include <jni.h>
#include <string>

extern "C" JNIEXPORT float JNICALL
Java_com_example_cardgame_InputActivity_calculateHour(JNIEnv* env, jobject obj,
                                                      jfloat hour, jfloat minute) {
    return hour + (minute / 60);
}
/*
extern "C" JNIEXPORT float JNICALL
Java_com_example_cardgame_FragmentGame_penguinMove(JNIEnv* env, jobject obj,){

}
*/
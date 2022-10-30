import {NativeModules, ToastAndroid} from 'react-native';

const {AudioManagerModule} = NativeModules;

const SetSpeaker = async (setSpeakerModeOn: boolean) => {
  try {
    const res = await AudioManagerModule.setSpeaker(setSpeakerModeOn);
    ToastAndroid.show(res, ToastAndroid.SHORT);
  } catch (error) {
    console.error(error);
  }
};

const GetSpeakerStatus = async (): Promise<boolean> => {
  const isSpeakerModeOn: boolean = await AudioManagerModule.getSpeakerMode();
  return isSpeakerModeOn;
};

export const AudioManager = {SetSpeaker, GetSpeakerStatus};

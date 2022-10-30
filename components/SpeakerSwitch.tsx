import React, {useState} from 'react';
import {Switch, Text, View} from 'react-native';
import {AudioManager} from '../utils/audio/AudioManager';

const SpeakerSwitch = () => {
  const [isToggled, setIsToggled] = useState<boolean>(false);

  const toggleSwitch = async (toggled: boolean) => {
    setIsToggled(toggled);
    await AudioManager.SetSpeaker(toggled);
  };

  return (
    <View className="flex flex-col justify-center items-center gap-5">
      <Text className=" font-bold text-lg">
        Speaker Mode :{' '}
        <Text className={isToggled ? 'text-emerald-500' : 'text-rose-500'}>
          {isToggled ? 'On' : 'Off'}
        </Text>
      </Text>
      <Switch
        className=""
        thumbColor={isToggled ? '#10b981' : '#f43f5e'}
        onValueChange={toggleSwitch}
        value={isToggled}
      />
    </View>
  );
};

export default SpeakerSwitch;

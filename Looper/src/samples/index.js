import funk_beats from "./audio/120_future_funk_beats_25.mp3";
import stutter_breakbeats from "./audio/120_stutter_breakbeats_16.mp3";
import Bass_Warwick from "./audio/Bass Warwick heavy funk groove on E 120 BPM.mp3";
import electric_guitar from "./audio/electric guitar coutry slide 120bpm - B.mp3";
import StompySlosh from "./audio/FUD_120_StompySlosh.mp3";
import Groove from "./audio/GrooveB_120bpm_Tanggu.mp3"
import MazePolitics from "./audio/MazePolitics_120_Perc.mp3";
import PAS3GROOVE1 from "./audio/PAS3GROOVE1.03B.mp3";
import SilentStar from "./audio/SilentStar_120_Em_OrganSynth.mp3";

import icon1 from './icons/1.png';
import icon2 from './icons/2.png';
import icon3 from './icons/3.png';
import icon4 from './icons/4.png';
import icon5 from './icons/5.png';
import icon6 from './icons/6.png';
import icon7 from './icons/7.png';
import icon8 from './icons/8.png';
import icon9 from './icons/9.png';

export const samplesArray = [
    {
        sample: new Audio(funk_beats),
        icon: icon1,
        idx: 1
    },
    {
        sample: new Audio(stutter_breakbeats),
        icon: icon2,
        idx: 2
    },
    {
        sample: new Audio(Bass_Warwick),
        icon: icon3,
        idx: 3
    },
    {
        sample: new Audio(electric_guitar),
        icon: icon4,
        idx: 4
    },
    {
        sample: new Audio(StompySlosh),
        icon: icon5,
        idx: 5
    },
    {
        sample: new Audio(Groove),
        icon: icon6,
        idx: 6
    },
    {
        sample: new Audio(MazePolitics),
        icon: icon7,
        idx: 7
    },
    {
        sample: new Audio(PAS3GROOVE1),
        icon: icon8,
        idx: 8
    },
    {
        sample: new Audio(SilentStar),
        icon: icon9,
        idx: 9
    },

];

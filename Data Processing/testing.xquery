
import schema namespace c = "http://cs548.stevens.edu/clinic/db" at "Assignment3-Xquery (1).xsd";

declare function local:getProviderInfo($klinic as element(c:clinic)) as element (providers)* {
    for $provider in $klinic//provider
    return
    <providers>
        {
        <Provider>
            Provider ID: {data($provider//npi)}
            Provider Name: {data($provider//name)}
        </Provider>
        }
        {
        for $patient in $klinic//patient
            where $patient//treatment//provider//npi = $provider//npi
            return <patients>
                {
                <patient>
                    Patient ID: {data($patient//patient-id)}
                    Patient Name: {data($patient//name)}
                </patient>
                }
                {
                    for $trmt in $patient//treatment
                    return
                        if ($trmt//provider//npi = $provider//npi) then
                        <li>Drug Treatment</li>
                        else ()
                }
                </patients>
        }
    </providers>
};

let $clinic := doc("instance1.xml")//c:clinic

return local:getProviderInfo($clinic)